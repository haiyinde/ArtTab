import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
// 메인 페이지
import Main from "@/views/Main.vue";
// 계정 페이지
import Accounts from "@/views/Accounts.vue";
import Login from "@/components/accounts/Login.vue";
import SignUp from "@/components/accounts/SignUp.vue";
import ConfirmEmail from "@/components/accounts/ConfirmEmail.vue";
import InsertNickname from "@/components/accounts/InsertNickname.vue";
import FindPassword from "@/components/accounts/FindPassword.vue";
// 내 정보 페이지
import Profile from "@/views/Profile.vue";
// 검색 결과 페이지
import SearchResult from "@/views/SearchResult.vue";
// 작품 페이지
import Artwork from "@/views/Artwork.vue";
import ArtworkDetail from "@/components/artwork/ArtworkDetail.vue";
import ArtworkCreate from "@/components/artwork/ArtworkCreate.vue";
import ArtworkUpdate from "@/components/artwork/ArtworkUpdate.vue";

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
    path: "/artwork",
    name: "Artwork",
    component: Artwork,
    children: [
      {
        path: "detail/:id",
        name: "ArtworkDetail",
        component: ArtworkDetail,
      },
      {
        path: "/artworkcreate",
        name: "ArtworkCreate",
        component: ArtworkCreate,
      },
      {
        path: "/artworkupdate",
        name: "ArtworkUpdate",
        component: ArtworkUpdate,
        props: true,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
