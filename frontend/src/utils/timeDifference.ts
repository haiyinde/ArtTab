import dayjs from "dayjs";

// 현재와의 시간 차이 계산
function diffTime(date: string): string {
  const today: dayjs.Dayjs = dayjs();
  const regDate: dayjs.Dayjs = dayjs(date);

  const difference = today.diff(regDate, "second");
  if (difference < 60) {
    return difference + "초 전";
  }
  if (difference < 60 * 60) {
    return Math.floor(difference / 60) + "분 전";
  }
  if (difference < 60 * 60 * 24) {
    return Math.floor(difference / (60 * 60)) + "시간 전";
  }
  return regDate.format("YYYY/MM/DD");
}

export { diffTime };
