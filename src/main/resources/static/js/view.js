

const mdViewer = {
    action : {
        setTitle : function () {
            // article 내 최초 h1의 텍스트를 title로 설정
            // h1~h6 중 가장 먼저 나오는 텍스트를 title로 설정
            const article = document.querySelector('article');
            const hList = article.querySelectorAll('h1, h2, h3, h4, h5, h6');
            let titleText = null;
            for (let i = 0; i < hList.length; i++) {
                if (hList[i].innerText) {
                    titleText = hList[i].innerText;
                    break;
                }
            }
            if (titleText) {
                document.title = titleText;
            }
        }
    },
}

document.addEventListener('DOMContentLoaded', function () {
    mdViewer.action.setTitle();
});