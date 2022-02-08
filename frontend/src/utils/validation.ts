// 이메일 형식 검사
function validateEmail(email: string): boolean {
  const regex = /^[a-zA-Z0-9_=.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
  return regex.test(email);
}

export { validateEmail };
