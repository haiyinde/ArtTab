import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
// 메인 페이지
import Main from "@/views/Main.vue";
// 계정 페이지
import Accounts from "@/views/Accounts.vue";
import Login from "@/components/Accounts/Login.vue";
import SignUp from "@/components/Accounts/SignUp.vue";
import ConfirmEmail from "@/components/Accounts/ConfirmEmail.vue";
import InsertNickname from "@/components/Accounts/InsertNickname.vue";
import FindPassword from "@/components/Accounts/FindPassword.vue";
// 내 정보 페이지
import Profile from "@/views/Profile.vue";
// 검색 결과 페이지
import SearchResult from "@/views/SearchResult.vue";
// 작품 페이지
import ArtworkDetail from "@/views/ArtworkDetail.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/accounts",
    name: "Accounts",
    component: Accounts,
    children: [
      {
        path: "login",
        name: "Login",
        component: Login,
      },
      {
        path: "signup",
        name: "SignUp",
        component: SignUp,
      },
      {
        path: "confirmemail",
        name: "ConfirmEmail",
        component: ConfirmEmail,
      },
      {
        path: "insertnickname",
        name: "InsertNickname",
        component: InsertNickname,
      },
      {
        path: "findpassword",
        name: "FindPassword",
        component: FindPassword,
      },
    ],
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/searchresult",
    name: "SearchResult",
    component: SearchResult,
  },
  {
    path: "/artworkdetail",
    name: "ArtworkDetail",
    component: ArtworkDetail,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
