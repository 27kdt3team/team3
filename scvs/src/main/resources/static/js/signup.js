document.addEventListener("DOMContentLoaded", () => {
    // 이메일 입력 시 검증 초기화
    document.getElementById("email").addEventListener("input", () => {
        const emailValidation = document.getElementById("email-validation");
        emailValidation.textContent = "";  // 경고 메시지 초기화
        document.getElementById("email").dataset.isValid = "false"; // 이메일 검증 상태 초기화
    });

    // 닉네임 입력 시 검증 초기화
    document.getElementById("nickname").addEventListener("input", () => {
        const nicknameValidation = document.getElementById("nickname-validation");
        nicknameValidation.textContent = "";  // 경고 메시지 초기화
        document.getElementById("nickname").dataset.isValid = "false"; // 닉네임 검증 상태 초기화
    });

    // 이메일 중복 확인 버튼 이벤트 리스너
    document.getElementById("check-email-duplicate").addEventListener("click", () => {
        const emailInput = document.getElementById("email").value.trim(); // 이메일 입력 값
        const emailValidation = document.getElementById("email-validation"); // 입력란 밑 경고메세지

        let isEmailValid = false; // 이메일 검증 상태 변수

        // 이메일 필수 항목 체크
        if (emailInput === "") {
            const emailValidation = document.getElementById("email-validation");
            emailValidation.textContent = "이메일은 필수 항목입니다.";
            emailValidation.style.color = "red";
            isEmailValid = false;
            return;
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput)) { // 이메일형식 @ . 을 검사
            const emailValidation = document.getElementById("email-validation");
            emailValidation.textContent = "이메일 형식이 아닙니다.";
            emailValidation.style.color = "red";
            isEmailValid = false;
            return;
        } else {
            checkEmailDuplicate(emailInput, isEmailValid); // 중복체크 함수 > 서버 호출
        }
        // 이메일 상태 변수 설정( 회원가입 제출시 이메일란 검증)
        emailInput.dataset.isValid = isEmailValid;
    });



    // 비밀번호 검증 함수
    document.getElementById("password").addEventListener("input", () => {
        const passwordInput = document.getElementById("password").value; // 비밀번호 입력 값
        const passwordValidation = document.getElementById("password-validation"); // 입력란 밑 경고메세지

        let isPasswordValid = false; // 비밀번호 검증 상태 변수

        // 비밀번호 필수 항목 체크
        if (passwordInput.trim() === "") {
            passwordValidation.textContent = "비밀번호는 필수 항목입니다.";
            passwordValidation.style.color = "red";
            isPasswordValid = false;
            return;
        }

        // 비밀번호 규격 체크 (8자 이상, 64자 이하, 특수문자, 대/소문자, 숫자 포함)
        const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,64}$/;
        if (!passwordPattern.test(passwordInput)) {
            passwordValidation.textContent = "비밀번호는 8자 이상 64자 이하, 특수문자, 영어, 숫자만 가능합니다.";
            passwordValidation.style.color = "red";
            isPasswordValid = false;
            return;
        } else {
            // 성공 메시지
            passwordValidation.textContent = "사용 가능한 비밀번호입니다.";
            passwordValidation.style.color = "green";
            isPasswordValid = true;
        }


        // 비밀번호 검증 상태 변수 설정( 회원가입 제출시 비밀번호란 검증)
        passwordInput.dataset.isValid = isPasswordValid;
    });

    //비밀번호-체크(같은값인지) 함수
    document.getElementById("password-check").addEventListener("input", () => {
        const passwordInput = document.getElementById("password").value;    //비밀번호 입력값
        const passwordCheckInput = document.getElementById("password-check").value; //비밀번호확인 입력값
        const passwordCheckValidation = document.getElementById("Password-check-validation");  //입력란 밑 경고메세지

        let isPasswordCheckValid = false; // 비밀번호체크 검증 상태 변수

        if (passwordInput === passwordCheckInput) {
            passwordCheckValidation.textContent = "비밀번호가 일치합니다.";
            passwordCheckValidation.style.color = "green";
            isPasswordCheckValid = true;
        } else {
            passwordCheckValidation.textContent = "비밀번호가 일치하지 않습니다.";
            passwordCheckValidation.style.color = "red";
            isPasswordCheckValid = false;
            return;
        }

        // 비밀번호체크 검증 상태 변수 설정( 회원가입 제출시 비밀번호체크란 검증)
        passwordCheckInput.dataset.isValid = isPasswordCheckValid;
    });




 // 닉네임 중복 확인 버튼 이벤트 리스너
  document.getElementById("check-nickname-duplicate").addEventListener("click", async () => {
      const nicknameInput = document.getElementById("nickname").value.trim(); // 닉네임 입력값
      const nicknameValidation = document.getElementById("nickname-validation"); //입력란 밑 경고메세지
      let isNicknameValid = false; // 닉네임 검증 상태

      // 닉네임이 비어있으면
      if (nicknameInput === "") {
          nicknameValidation.textContent = "닉네임은 필수항목입니다.";
          nicknameValidation.style.color = "red";
          isNicknameValid = false;
          return;
      }

      // 닉네임 유효성 체크 (2~15자, 영/한/숫자, 띄어쓰기 금지)
      const nicknamePattern = /^[a-zA-Z0-9가-힣]{2,15}$/;
      if (!nicknamePattern.test(nicknameInput)) {
          nicknameValidation.textContent = "닉네임은 2자 이상 15자 이하, 영/한/숫자만 가능합니다.";
          nicknameValidation.style.color = "red";
          isNicknameValid = false;
          return;
      } else {
          checkNicknameDuplicate(nicknameInput, isNicknameValid); // 서버 호출
      }

      // 닉네임 상태 변수 설정( 회원가입 제출시 닉네임란 검증)
      nicknameInput.dataset.isValid = isNicknameValid; // 변수 이름 수정
  });


    // 폼 제출 시 이메일 검증 상태 확인
    document.querySelector("form").addEventListener("submit", (event) => {
        const isEmailValid = document.getElementById("email").dataset.isValid === "true"; // 이메일 검증 상태 읽기
        const isPasswordValid = document.getElementById("password").dataset.isValid === "true"; // 비밀번호 검증 상태 읽기
        const isPasswordCheckValid = document.getElementById("password-check").dataset.isValid === "true"; // 비밀번호체크 검증 상태 읽기
        const isNicknameValid = document.getElementById("nickname").dataset.isValid === "true"; // 닉네임 검증 상태 읽기

        if (!isEmailValid) {
            event.preventDefault(); // 제출 중단
            alert("아이디 중복검사를 진행해 주세요!");
        }else if (!isPasswordValid || !passwordCheckValidation) {
            event.preventDefault(); // 제출 중단
            alert("입력란 경고문을 확인해 주세요!");
        }else if (!isNicknameValid){
            event.preventDefault(); // 제출 중단
            alert("닉네임 중복검사를 진행해 주세요!");
        }
    });
});


// 이메일 중복 확인 (서버 호출)
function checkEmailDuplicate(emailInput, isEmailValid) {
    fetch("/api/check-email", {
        method: "POST", // POST 방식 지정
        headers: {
            "Content-Type": "application/json", // JSON 형식 지정
        },
        body: JSON.stringify({ email: emailInput }), // 요청 본문에 데이터 포함 문자열로 변환
    })
        .then((response) => response.json()) // 서버 응답(JSON)을 파싱
        .then((isDuplicate) => {
            const emailValidation = document.getElementById("email-validation");

            if (isDuplicate) {
                emailValidation.textContent = "이미 사용 중인 이메일입니다.";
                emailValidation.style.color = "red";
                isEmailValid = false;
            } else {
                emailValidation.textContent = "사용 가능한 이메일입니다.";
                emailValidation.style.color = "green";
                isEmailValid = true;
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });
}

// 닉네임 중복 확인 함수 (서버 호출)
function checkNicknameDuplicate(nicknameInput, isNicknameValid) {
    fetch("/api/check-nickname", {
        method: "POST", // POST 방식 지정
        headers: {
            "Content-Type": "application/json", // 요청 본문을 JSON 형식으로 지정
        },
        body: JSON.stringify({ nickname: nicknameInput }), // JSON 형식으로 닉네임 데이터 전송
    })
        .then((response) => response.json()) // 서버 응답(JSON)을 파싱
        .then((isDuplicate) => {
            const nicknameValidation = document.getElementById("nickname-validation");

            if (isDuplicate) {
                nicknameValidation.textContent = "이미 사용 중인 닉네임입니다.";
                nicknameValidation.style.color = "red";
                isNicknameValid = false;
            } else {
                nicknameValidation.textContent = "사용 가능한 닉네임입니다.";
                nicknameValidation.style.color = "green";
                isNicknameValid = true;
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            const nicknameValidation = document.getElementById("nickname-validation");
            nicknameValidation.textContent = "중복 확인 중 오류가 발생했습니다.";
            nicknameValidation.style.color = "red";
        });
}

