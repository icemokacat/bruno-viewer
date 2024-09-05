

const mdViewer = {
    action : {
        setTitle : function () {
            // article 내 최초 h1의 텍스트를 title로 설정
            const titleText = document.querySelector('article h2').innerText;
            if (titleText) {
                document.title = titleText;
            }
        }
    },
}

document.addEventListener('DOMContentLoaded', function () {
    mdViewer.action.setTitle();
});