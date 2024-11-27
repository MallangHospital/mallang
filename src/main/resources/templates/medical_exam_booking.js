$(function() {
  $("#datepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    showOtherMonths: true,
    selectOtherMonths: true,
    showButtonPanel: true,
    onSelect: function(selectedDate) {
      validateDate(selectedDate);  // 날짜가 선택되면 유효성 검사 함수 호출
    }
  });
});

// 예약 날짜가 현재 날짜 이전이면 alert 표시
function validateDate(selectedDate) {
  const selectedDateObj = new Date(selectedDate);
  const currentDate = new Date();

  // 현재 날짜의 시간, 분, 초를 0으로 설정 (시간 비교를 정확하게 하기 위해)
  currentDate.setHours(0, 0, 0, 0);

  // 선택한 날짜가 현재 날짜보다 이전이면 경고 메시지
  if (selectedDateObj < currentDate) {
    alert("예약 날짜는 현재 날짜 이후로 선택해 주세요.");
    $("#datepicker").val('');  // 날짜 입력을 초기화
  }
}

// 팝업 표시/닫기 함수
function showCompletionPopup() {
  document.getElementById("completionPopup").style.display = "flex";
}

function closeCompletionPopup() {
  document.getElementById("completionPopup").style.display = "none";
  window.location.href = "home.html"; // 홈 페이지로 이동
}

// 예약 폼 유효성 검사 및 서버로 전송
function validateAndSubmitForm(event) {
  event.preventDefault();

  const date = document.getElementById('datepicker').value;
  const name = document.getElementById('name').value.trim();
  const phoneNo = document.getElementById('phoneNo').value.trim();
  const checkupCd = document.querySelector('input[name="checkupCd"]:checked');
  const termsChecked = document.getElementById('chk02').checked;  // 동의 체크 여부 확인

  // 휴대폰 번호 정규식
  const phonePattern = /^01[016789]-\d{3,4}-\d{4}$/;

  // 유효성 검사
  if (!date) {
      alert("예약 날짜를 선택해 주세요.");
      return;
  }
  if (!name) {
      alert("이름을 입력해 주세요.");
      return;
  }
  if (!phoneNo) {
      alert("휴대폰번호를 입력해 주세요.");
      return;
  }
  if (!phonePattern.test(phoneNo)) {
      alert("휴대폰 번호를 010-0000-0000 형식으로 입력해주세요.");
      return;
  }
  if (!checkupCd) {
      alert("검진 구분을 선택해 주세요.");
      return;
  }

  if (!termsChecked) {
    alert("이용약관에 동의하셔야 예약을 진행할 수 있습니다.");
    return;
  }

  // 서버로 전송할 데이터 준비
  const requestData = {
      name: name,
      phoneNumber: phoneNo,
      reserveDate: date,
      hType: checkupCd.value, 
  };

  // 전송 데이터 로그 추가
  console.log("서버로 전송되는 데이터:", requestData);

  // Heroku 서버로 예약 요청 전송
  $.ajax({
      url: "https://mallang-a85bb2ff492b.herokuapp.com/api/member/healthcareReserve",  
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(requestData),
      success: function (response) {
          showCompletionPopup(); // 예약 성공 후 팝업 표시
      },
      error: function (xhr) {
          alert("예약 처리 중 문제가 발생했습니다. 다시 시도해 주세요.");
      },
  });
}

$(document).ready(function () {
  $("#rFrm").on("submit", validateAndSubmitForm); // 폼 제출 시 유효성 검사
});
