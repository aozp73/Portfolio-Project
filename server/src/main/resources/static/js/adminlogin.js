window.addEventListener('scroll', function() {
    let navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) { 
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked ? "on" : "off";

    var loginDTO = {
        email: email,
        password: password,
        remember: remember
    };

    $.ajax({
        url: '/login',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(loginDTO),

        success: function(data, textStatus, jqXHR) {
            // 올바른 로그인 정보를 입력하였다면, Service-DB 조회 후 Header에 토큰 넣어서 응답
            const jwtToken = jqXHR.getResponseHeader('Authorization'); 
            if (jwtToken) {
                // 해당 토큰 저장
                localStorage.setItem('jwtToken', jwtToken);
                window.location.href = '/mainpage'; 
            } else {
                alert("내부 서버에러 발생 (code 1)")
            }
        },
        error: function(error) {
            alert(error.responseJSON.data)
        }
    });
}

// ======= 아이디 기억하기 기능 ====================
function getCookie(name) {
    var value = document.cookie;
    var parts = value.split(name);
    return parts.length == 2;
}

window.onload = function() {
    var rememberCheck = getCookie('remember');
    if (rememberCheck) {
        document.getElementById('remember').checked = true;
    } else {
        document.getElementById('remember').checked = false;
    }
};
// ======= 아이디 기억하기 기능 ====================
