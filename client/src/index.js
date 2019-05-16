import FormData from 'form-data';
import axios from 'axios';

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById('choose').onchange = previewFile;
    document.getElementById('submit').onclick = submit;

    function previewFile() {
        let preview = document.querySelector('img');
        const file    = document.querySelector('input[type=file]').files[0];
        const reader  = new FileReader();
    
        reader.onloadend = function () {
            preview.src = reader.result;
        }
    
        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "";
        }
    }
    
    function submit() {
        let data = new FormData();
        const file = document.querySelector('input[type=file]').files[0];
        const reader  = new FileReader();

        reader.onloadend = () => {
            axios.post('http://localhost:8000/api/hello', reader.result, { headers: { accept: 'application/json', 'Accept-Language': 'pt-BR' } })
            .then((response) => {
                console.log(response)
            }).catch((error) => {
                console.error(error)
            })
        }
        
        reader.readAsBinaryString(file)
        data.append('file', file);
    }
});
