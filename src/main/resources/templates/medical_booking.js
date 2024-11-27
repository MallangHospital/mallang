function saveSelectionAndProceed() {
    // 진료과 ID 가져오기
    const departmentElement = document.querySelector('#listView .txt .tit');
    const departmentId = departmentElement ? departmentElement.closest('li').getAttribute('data-id') : null;

    // 의료진 ID 가져오기
    const doctorElement = document.querySelector('#staffListView input[name="doctor"]:checked');
    const doctorId = doctorElement ? doctorElement.value : null;

    // 진료 유형 가져오기
    const appointmentTypeElement = document.querySelector('input[name="option"]:checked');
    const appointmentType = appointmentTypeElement ? appointmentTypeElement.id : null; // 예: 초진, 재진, 상담

    // 유효성 검사
    if (!appointmentType) {
        alert("진료 유형을 선택해주세요.");
        return;
    }
    if (!departmentId) {
        alert("진료 과목을 선택해주세요.");
        return;
    }
    if (!doctorId) {
        alert("의료진을 선택해주세요.");
        return;
    }
    

    // 로컬 스토리지에 저장
    localStorage.setItem("departmentId", departmentId); // 진료과 ID 저장
    localStorage.setItem("doctorId", doctorId);         // 의료진 ID 저장
    localStorage.setItem("appointmentType", appointmentType); // 진료 유형 저장

    // 다음 페이지로 이동
    window.location.href = "medical_booking_step2.html";
}

// 이벤트 리스너 추가
document.querySelector('.btn_next').addEventListener('click', saveSelectionAndProceed);
