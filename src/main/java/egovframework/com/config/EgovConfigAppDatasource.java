package egovframework.com.config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

/**
 * @ClassName : EgovConfigAppDatasource.java
 * @Description : DataSource 설정
 *
 * @author : 윤주호
 * @since : 2021. 7. 20
 * @version : 1.0
 *
 *          <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 *   2021. 7. 20    윤주호               최초 생성
 *          </pre>
 *
 */
@Configuration
@PropertySources({ @PropertySource("classpath:/egovframework/egovProps/globals.properties") }) // CAUTION: min JDK 8
public class EgovConfigAppDatasource {

	@Autowired
	Environment env;

	private String dbType;

	private String className;

	private String dataSourceClassName;

	private String url;

	private String userName;

	private String password;

	@PostConstruct
	void init() {
		dbType = env.getProperty("Globals.DbType");
		// Exception 처리 필요
		className = env.getProperty("Globals." + dbType + ".DriverClassName");
		dataSourceClassName = env.getProperty("Globals." + dbType + ".DataSourceClassName");
		url = env.getProperty("Globals." + dbType + ".Url");
		userName = env.getProperty("Globals." + dbType + ".UserName");
		password = env.getProperty("Globals." + dbType + ".Password");
	}

	/**
	 * @return [dataSource 설정] HSQL 설정
	 */
	private DataSource dataSourceHSQL() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setScriptEncoding("UTF8")
				.addScript("classpath:/db/shtdb.sql")
				.build();
	}

	/**
	 * @return [dataSource 설정] basicDataSource 설정
	 */
	private DataSource basicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(className);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		return basicDataSource;
	}

	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName("schdl_mng_DBPool");
		hikariConfig.setRegisterMbeans(true);
		hikariConfig.setConnectionTestQuery("SELECT 1");

		hikariConfig.setAutoCommit(true);
		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setValidationTimeout(5000);
		hikariConfig.setIdleTimeout(60000);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(1);
		hikariConfig.setMaxLifetime(600000);
		hikariConfig.setLeakDetectionThreshold(2000);

		hikariConfig.setDataSourceClassName(dataSourceClassName);
		Properties dataSourceProperties = new Properties();
		dataSourceProperties.setProperty("url", url);
		dataSourceProperties.setProperty("user", userName);
		dataSourceProperties.setProperty("password", password);
		hikariConfig.setDataSourceProperties(dataSourceProperties);

		return hikariConfig;

	}

	public HikariDataSource dataSourceSpied() {
		return new HikariDataSource(hikariConfig());
	}

	private DataSource hikariDataSource() {
		Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(dataSourceSpied());
		dataSource.setLogFormatter(logFormatter());
		return dataSource;
	}

	public Log4JdbcCustomFormatter logFormatter() {
		Log4JdbcCustomFormatter logFormatter = new Log4JdbcCustomFormatter();
		logFormatter.setLoggingType(LoggingType.MULTI_LINE);
		logFormatter.setSqlPrefix("");
		return logFormatter;
	}

	/**
	 * @return [DataSource 설정]
	 */
	@Bean(name = { "dataSource", "egov.dataSource", "egovDataSource" })
	public DataSource dataSource() {
		if ("hsql".equals(dbType)) {
			return dataSourceHSQL();
		} else {
			return hikariDataSource();
		}
	}
}
