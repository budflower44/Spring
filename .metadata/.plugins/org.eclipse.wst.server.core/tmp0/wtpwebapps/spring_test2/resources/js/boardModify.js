console.log("modify in");

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('flie-x')){
        let uuid = e.target.dataset.uuid;
        console.log(uuid);
        removeFileToServer(uuid).then(result =>{
            if(result === "1"){
                alert("파일 삭제 성공");
                e.target.closest('li').remove();
            }
        })
    }
})

async function removeFileToServer(uuid){
    try {
        const url = "/board/file/"+uuid;
        const config = {
            method:"delete"
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.getElementById('logoutLink').addEventListener('click', (e)=>{
    //원래 기능 실행하지 마
    e.preventDefault();
    document.getElementById('logoutForm').submit();
})
