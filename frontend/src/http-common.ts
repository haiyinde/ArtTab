import axios, { AxiosInstance } from "axios";
import { BASE_URL } from "@/config";

// axios 객체 생성
const apiInstance: AxiosInstance = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
});

export default apiInstance;
