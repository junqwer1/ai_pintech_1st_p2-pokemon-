window.addEventListener("DOMContentLoaded", function() {
    const el = document.querySelector(".profile-image");
    el.addEventListener("dblclick", function() {
        const seq = this.dataset.seq;
<<<<<<< HEAD
        if (!seq || !confirm("정말 삭제하겠습니까?")) {
=======
        if (!seq || !confirm('정말 삭제하겠습니까?')) {
>>>>>>> ba472bc3c532f47aae6f25154e814b95cb9f3fed
            return;
        }

        const { fileManager } = commonLib;
<<<<<<< HEAD
        fileManager.delete(seq, function() {
//            삭제 후 후속 처리
=======
        fileManager.delete(seq, function(file) {
            // 삭제 후 후속 처리
>>>>>>> ba472bc3c532f47aae6f25154e814b95cb9f3fed
            delete el.dataset.seq;
            el.innerHTML = "";
        });

    });
<<<<<<< HEAD
})
=======
});
>>>>>>> ba472bc3c532f47aae6f25154e814b95cb9f3fed

/*
파일 업로드 후속 처리
*/
function callbackFileUpload(files) {
    if (!files || files.length === 0) {
        return;
    }

    const el = document.querySelector(".profile-image");
    if (el) {
        const file = files[0];

        el.dataset.seq = file.seq;

        el.innerHTML = `<img src='${file.thumbUrl}&width=250&height=350'>`;
    }
}