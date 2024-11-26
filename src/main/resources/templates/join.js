// 아이디 중복 확인
async function idCheck() {
  const userId = document.getElementById('userId').value;

  if (!userId) {
    alert("아이디를 입력하세요.");
    return false;
  }

  try {
    const response = await fetch(`/api/member/check-id/${userId}`, {
      method: 'GET',
    });

    if (response.ok) {
      const message = await response.text();
      alert(message); // 성공 메시지
    } else {
      const errorMessage = await response.text();
      alert(errorMessage); // 에러 메시지
    }
  } catch (error) {
    alert(`아이디 중복 확인 실패: ${error.message}`);
  }

  return false;
}

// 필수 필드 검증
function validateRequiredFields() {
  const requiredFields = [
    { id: 'name', message: '이름을 입력하세요.' },
    { id: 'userId', message: '아이디를 입력하세요.' },
    { id: 'email', message: '이메일 주소를 입력하세요.' },
    { id: 'phoneNum', message: '휴대전화 번호를 입력하세요.' },
    { id: 'pswd1', message: '비밀번호를 입력하세요.' },
    { id: 'rrn', message: '주민등록번호를 입력하세요.' }
  ];

  for (const field of requiredFields) {
    const value = document.getElementById(field.id).value.trim();
    if (!value) {
      alert(field.message);
      return false;
    }
  }

  return true;
}

// 주민등록번호 검증
function validateAdditionalFields() {
  const rrn = document.getElementById('rrn').value;  
  const rrnRegex = /^\d{6}-\d{7}$/;

  if (!rrnRegex.test(rrn)) {
    alert("주민등록번호는 123456-1234567 형식으로 입력하세요.");
    return false;
  }

  return true;
}

// 비밀번호 및 전화번호 검증
function validatePasswords() {
  const password = document.getElementById('pswd1').value;
  const confirmPassword = document.getElementById('pswd2').value;
  const phoneNum = document.getElementById('phoneNum').value;

  const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
  if (!phoneRegex.test(phoneNum)) {
    alert("휴대전화 번호는 010-0000-0000 형식으로 입력해주세요.");
    return false;
  }

  const passwordRegex = /^.{8,}$/; // 최소 8자 이상만 검증
  if (!passwordRegex.test(password)) {
    alert("비밀번호는 최소 8자 이상이어야 합니다.");
    return false;
  }

  if (password !== confirmPassword) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  return true;
}

// 폼 데이터 검증 및 서버 전송
async function submitForm() {
  const submitButton = document.querySelector(".btn_submit");
  submitButton.disabled = true;

  if (!validateRequiredFields() || !validateAdditionalFields() || !validatePasswords()) {
    submitButton.disabled = false; // 검증 실패 시 다시 활성화
    return;
  }

  const formData = {
    mid: document.getElementById('userId').value.trim(),
    mpw: document.getElementById('pswd1').value,
    email: document.getElementById('email').value.trim(),
    name: document.getElementById('name').value.trim(),
    phoneNum: document.getElementById('phoneNum').value.trim(),
    rrn: document.getElementById('rrn').value.trim(),
    agreeToTerms: localStorage.getItem('agreeToTerms') === 'true'
  };

  try {
    const response = await fetch('/api/member/join', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      const message = await response.text();
      showPopup(); // 성공 팝업 표시
    } else {
      const errorMessage = await response.text();
      alert(errorMessage); // 실패 메시지 표시
    }
  } catch (error) {
    alert(`회원가입 실패: ${error.message}`);
  } finally {
    submitButton.disabled = false; // 에러든 성공이든 버튼 활성화
  }

  // 서버 상태 확인
  try {
    const statusResponse = await fetch('/api/status', { method: 'GET' });
    if (statusResponse.ok) {
      const status = await statusResponse.text();
      console.log("서버 상태:", status);
    } else {
      alert("서버 상태 확인 실패");
    }
  } catch (error) {
    alert("서버 연결에 실패했습니다.");
  }
}
