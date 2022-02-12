<template>
  <div>
    <!-- 페이지 제목 -->
    <page-title title="로그인"></page-title>

    <!-- 이메일 입력 -->
    <label for="email" class="label-text">이메일</label>
    <input
      type="email"
      id="email"
      class="input-text"
      name="email"
      v-model="account.email"
      autocapitalize="none"
    />
    <span class="alert" v-show="valid.email">가입되지 않은 이메일입니다.</span>

    <!-- 비밀번호 입력 -->
    <label for="password" class="label-text">비밀번호</label>
    <input-password
      :password="account.password"
      id="password"
      @inputVal="updatePassword"
      @keyup.enter="checkEmail"
    ></input-password>

    <div class="addition-box">
      <div>
        <input type="checkbox" id="storeId" name="storeId" v-model="storeId" />
        <label for="storeId">아이디 저장</label>
      </div>
      <router-link to="FindPassword">비밀번호 찾기</router-link>
    </div>

    <!-- 로그인 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="checkEmail"
    >
      로그인
    </button>

    <!-- SNS 로그인/회원가입 -->
    <div class="subtitle">SNS로 간편 로그인/회원가입하기</div>
    <div class="snslogin">
      <img class="circle" src="@/assets/images/kakao.png" alt="카카오 로그인" />
      <img class="circle" src="@/assets/images/google.png" alt="구글 로그인" />
      <!-- <img src="@/assets/images/kakaologin.png" alt="카카오 로그인" />
      <img src="@/assets/images/googlelogin.png" alt="카카오 로그인" /> -->
    </div>

    <!-- 이메일 가입 버튼 -->
    <div class="subtitle">계정이 없으신가요?</div>
    <button class="btn-white" @click="moveSignUp">이메일로 가입하기</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import InputPassword from "@/components/accounts/child/InputPassword.vue";
import AccountsAPI from "@/apis/accountsAPI";
import ResponseData from "@/types/ResponseData";
import { useCookies } from "vue3-cookies";
import { mapState, mapActions } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "Login",
  components: {
    PageTitle,
    InputPassword,
  },
  data() {
    return {
      account: {
        email: "",
        password: "",
      },
      storeId: "",
      valid: {
        email: false,
      },
      isCompleted: false,
      cookies: useCookies().cookies,
    };
  },
  // 이전에 아이디 저장을 한 경우 아이디 불러오기
  mounted() {
    this.account.email = this.cookies.get("idCookie");
    if (this.account.email) {
      this.storeId = "true";
    }
  },
  computed: {
    ...mapState(accountsStore, ["isConfirmEmail"]),
  },
  watch: {
    "account.email": function () {
      this.actButton();
    },
    "account.password": function () {
      this.actButton();
    },
  },
  methods: {
    ...mapActions(accountsStore, ["getLogin"]),
    // 비밀번호 컴포넌트에 입력된 텍스트 가져오기
    updatePassword(value: string) {
      this.account.password = value;
    },
    actButton() {
      this.valid.email = false;
      this.isCompleted =
        this.account.password && this.account.email ? true : false;
    },
    checkEmail() {
      // 임시 -----------
      this.login();
      // 임시 -----------

      // 아이디 가입여부 확인(이메일 중복 확인과 반대)
      // AccountsAPI.checkEmail(this.account.email)
      //   .then((res: ResponseData) => {
      //     if (res.data === "success") {
      //       this.valid.email = true;
      //     } else {
      //       this.login();
      //     }
      //   })
      //   .catch(() => {
      //     this.valid.email = false;
      //     alert("이메일 가입 여부 확인 중 오류가 발생했습니다.");
      //   });
    },
    async login() {
      // 아이디 저장을 체크한 경우 쿠키에 저장하기
      if (this.storeId) {
        this.cookies.set("idCookie", this.account.email, "30d");
      }
      // 아이디 저장을 체크하지 않은 경우(체크 해제) 쿠키에서 삭제하기
      else {
        this.cookies.remove("idCookie");
      }
      // 로그인 처리하기
      await this.getLogin(this.account);
      // 이메일 인증을 완료하지 않은 사용자일 경우 이메일 인증 페이지로 이동
      if (this.isConfirmEmail) {
        this.$router.push({
          name: "ConfirmEmail",
        });
      }
      // 이메일 인증을 완료한 사용자일 경우 이전페이지로 또는 메인페이지로 이동
      else {
        this.$router.back();
        this.$router.push({ name: "Main" });
      }
    },
    moveSignUp() {
      this.$router.push({ name: "SignUp" });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";

.addition-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: $size-small;
  font-size: $font-small;
}

.subtitle {
  margin-top: $size-big;
}

.snslogin {
  text-align: center;
  margin-top: $size-medium;
}

img {
  width: 100%;
  cursor: pointer;
  &.circle {
    width: 3.5rem;
    margin: 0 $size-micro;
  }
}
</style>
