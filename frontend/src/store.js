import { createStore, combineReducers } from "redux";
import { persistReducer, persistStore } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";

const reducer = (state = {}, action) => {
    switch (action.type) {
        case "LOGIN_SESSION":
            return { ...state, loginVO: action.loginVO };
        default:
            return state;
    }
};

const persistConfig = {
    key: "reducer",
    storage: storageSession,
};

//리듀서를 리듀서 모음에 결합 (스토어와 리듀서는 일 대 다 관계)
const reducers = combineReducers({ reducer: reducer });
//세션스토리지에 리듀서를 해당 키로 저장할 것을 명시
const persistedReducer = persistReducer(persistConfig, reducers);
//스토어 생성
export const store = createStore(
    persistedReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
//persistor 생성
export const persistor = persistStore(store);
