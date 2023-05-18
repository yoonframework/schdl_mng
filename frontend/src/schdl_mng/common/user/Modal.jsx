/**
 * 모달 Component
 * @param {*} modalInfo - 모달정보
 * @param {*} setModalInfo - 모달정보 setting 함수
 * 모달정보 내 Parameter
 * @param {*} showModal - 모달 보여 주다 (true : 모달 Open, false : 모달 Close)
 * @param {*} isLoadingUI - 모달 로딩 UI 표시 여부 (true : 로딩 UI 활성화, false : 로딩 UI 비활성화)
 * @param {*} width - 모달 가로 사이즈
 * @param {*} height - 모달 세로 사이즈
 * @param {*} title - 모달 제목
 * @param {*} content - 모달 내용 컴포넌트
 * @returns
 */
function Modal({ modalInfo, setModalInfo }) {
    //모달 정보 내 Key 값을 변수로 지정
    const { showModal, width, height, title, content } = modalInfo;

    // 모달 일련번호 생성
    const today = new Date();
    const modalUID =
        today.getDate() +
        today.getHours() +
        today.getMinutes() +
        today.getSeconds();
    const modalId = "modal" + modalUID;

    // 모달 창 닫기 이벤트
    const onModalCloseHandler = () => {
        setModalInfo((prevState) => {
            return {
                ...prevState,
                showModal: false,
                isLoadingUI: false,
                width: 0,
                height: 0,
                title: "",
                content: "",
            };
        });
    };

    return (
        <div id={modalId} className={`modal ${showModal && "open"}`}>
            <div className="modal_wrap">
                <div className={`modal_box width${width} height${height}`}>
                    <div className="modal_title">
                        <h3>{title}</h3>
                        <div className="close btn_close_wrap">
                            <button
                                type="button"
                                id="btnClose"
                                className="btn_close"
                                title="모달닫기버튼"
                                onClick={onModalCloseHandler}
                            >
                                <span className="blind">모달 닫기버튼</span>
                            </button>
                        </div>
                    </div>
                    {content}
                </div>
            </div>
        </div>
    );
}

export default Modal;
