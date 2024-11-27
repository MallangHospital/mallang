// 로그인 폼 제출 처리
document.getElementById('loginFrm').addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 동작 방지

    // 사용자 입력값 가져오기
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // 필수 입력값 검증
    if (!username || !password) {
        alert("아이디와 비밀번호를 모두 입력해주세요.");
        return;
    }

    console.log("로그인 요청:", { username, password }); // 요청 데이터 로그

    try {
        // 로그인 요청 보내기 (Heroku API 경로)
        const response = await fetch('https://mallang-a85bb2ff492b.herokuapp.com/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }), // 입력한 아이디와 비밀번호를 JSON으로 전송
        });

        // 서버 응답 처리
        if (response.ok) {
            const data = await response.json(); // 응답을 JSON으로 처리
            console.log("서버 응답:", data);

            // 받은 토큰을 로컬 스토리지에 저장
            localStorage.setItem('jwtToken', data.token); // 로그인 후 토큰 저장
            alert("로그인 성공!");
            window.location.href = '/home.html'; // 성공 시 홈 페이지로 리다이렉트
        } else {
            // 로그인 실패 시
            const errorMessage = await response.text();
            console.log("서버 응답 실패:", errorMessage); // 에러 메시지 로그 추가
            alert(`로그인 실패: ${errorMessage}`);
        }
    } catch (error) {
        // 네트워크 오류 또는 다른 오류 발생 시
        console.error("로그인 오류:", error);
        alert(`오류 발생: ${error.message}`);
    }
});
