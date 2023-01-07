// Get a reference to the login form
const loginForm = document.querySelector('form');

// 이벤트 리스너 연결
loginForm.addEventListener('submit', function(event) {
  event.preventDefault();

  // 아이디 및 암호 필드값 가져오기
  const username = document.getElementById ('user_id').value;
  const password = document.getElementById ('loginpw').value;

  // 유효성검사
  if (username === '' || password === '') {
    // 아이디 또는 비번이 비어있으면 오류메세지
    alert('Please enter a username and password');
    return;
  }
  fetch('/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.string({
      user_id: user_id,
      loginpw: loginpw
    })
  })
  .then(response => response.json())
  //데이터값 일치하지 않을시 에러메세지
//   .then(data => {
//     if (data.error) {
//       //
//       alert(data.error);
//     } else {
//       //
//       window.location.href = //
        
//     }
//   });
});
