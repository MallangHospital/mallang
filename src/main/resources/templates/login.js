// 로그인 폼 제출 처리
document.getElementById('loginFrm').addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 동작 방지
  
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
  
    if (!username || !password) {
        alert("아이디와 비밀번호를 모두 입력해주세요.");
        return;
    }

    console.log("로그인 요청:", { username, password }); // 요청 데이터 로그

    try {
        // Heroku API 경로
        const response = await fetch('https://mallang-a85bb2ff492b.herokuapp.com/api/member/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        console.log("API 응답 상태:", response.status); // 응답 상태 로그

        if (response.ok) {
            const data = await response.json();
            console.log("로그인 성공 데이터:", data); // 성공 데이터 로그
            alert("로그인 성공!");

            // 성공 시 리다이렉트
            window.location.href = '/home.html';
        } else {
            const errorMessage = await response.text();
            console.error("로그인 실패 메시지:", errorMessage); // 실패 메시지 로그
            alert(`로그인 실패. 다시 시도해주세요.`);
        }
    } catch (error) {
        console.error("로그인 요청 중 오류 발생:", error); // 오류 로그
        alert(`오류 발생. 다시 시도해주세요.`);
    }
});
