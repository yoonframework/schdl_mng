/**
 * 푸터 Component
 * @returns
 */
function Footer() {
    return (
        <footer>
            <div className="footer">
                <ul>
                    <li>
                        <h2 className="logo" tabIndex="0">
                            <a href={URL.ROOT}></a>
                        </h2>
                        <dl className="footer_info">
                            <dt></dt>
                            <dd></dd>
                        </dl>
                    </li>
                </ul>
                <p className="text_bottom">
                    copyright(C) KKY. all rights reserved
                </p>
            </div>
        </footer>
    );
}

export default Footer;
