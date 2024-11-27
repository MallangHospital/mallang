// 로그인 폼 제출 처리
document.getElementById('loginFrm').addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 동작 방지
  
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
  
    if (!username || !password) {
        alert("아이디와 비밀번호를 모두 입력해주세요.");
        return;
    }

    try {
        // Heroku API 경로
        const response = await fetch('https://mallang-a85bb2ff492b.herokuapp.com/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });


        if (response.ok) {
            const data = await response.json();
            alert("로그인 성공!");

            // 성공 시 리다이렉트
            window.location.href = '/home.html';
        } else {
            const errorMessage = await response.text();
            alert(`로그인 실패. 다시 시도해주세요.`);
        }
    } catch (error) {
        alert(`오류 발생. 다시 시도해주세요.`);
    }
});
