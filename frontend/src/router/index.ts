import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import store from "@/store";

// 메인 페이지
import Main from "@/views/Main.vue";
// 계정 페이지
import Accounts from "@/views/Accounts.vue";
import Login from "@/components/accounts/Login.vue";
import SignUp from "@/components/accounts/SignUp.vue";
import ConfirmEmail from "@/components/accounts/ConfirmEmail.vue";
import InsertNickname from "@/components/accounts/InsertNickname.vue";
import FindPassword from "@/components/accounts/FindPassword.vue";
import KakaoLogin from "@/components/accounts/snslogin/KakaoLogin.vue";
import GoogleLogin from "@/components/accounts/snslogin/GoogleLogin.vue";
// 내 정보 페이지
import Profile from "@/views/Profile.vue";
import DecorateGallery from "@/views/DecorateGallery.vue";
// 검색 결과 페이지
import SearchResult from "@/views/SearchResult.vue";
// 작품 페이지
import Artwork from "@/views/Artwork.vue";
import ArtworkDetail from "@/components/artwork/ArtworkDetail.vue";
import ArtworkCreate from "@/components/artwork/ArtworkCreate.vue";
import ArtworkUpdate from "@/components/artwork/ArtworkUpdate.vue";
// 404 페이지
import NotFound from "@/views/NotFound.vue";

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
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "signup",
        name: "SignUp",
        component: SignUp,
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "confirmemail",
        name: "ConfirmEmail",
        component: ConfirmEmail,
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "insertnickname",
        name: "InsertNickname",
        component: InsertNickname,
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "findpassword",
        name: "FindPassword",
        component: FindPassword,
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "kakaologin",
        name: "KakaoLogin",
        component: KakaoLogin,
        meta: {
          notRequireLogin: true,
        },
      },
      {
        path: "googlelogin",
        name: "Googlelogin",
        component: GoogleLogin,
        meta: {
          notRequireLogin: true,
        },
      },
    ],
  },
  {
    path: "/profile/:id",
    name: "Profile",
    component: Profile,
    props: true,
  },
  {
    path: "/decogallery",
    name: "DecorateGallery",
    component: DecorateGallery,
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
  {
    path: "/404",
    name: "NotFound",
    component: NotFound,
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach(function (to, from, next) {
  const isLogin = store.getters["accountsStore/checkLogin"];
  const userInfo = store.getters["accountsStore/checkUserInfo"];

  // to: 이동할 url에 해당하는 라우팅 객체
  if (
    to.matched.some(function (routeInfo) {
      return routeInfo.meta.notRequireLogin;
    })
  ) {
    // 로그인 필요없는 페이지(로그인, 회원가입 관련)
    next();
  } else if (isLogin && userInfo) {
    // 로그인 한 경우 클릭한 페이지로 이동
    next();
  } else {
    // 로그인 하지 않은 경우 로그인 페이지로 이동
    next("/accounts/login");
  }
});

export default router;
