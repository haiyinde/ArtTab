<template>
  <div>
    <page-title title="닉네임 입력하기" step="3" total="3"></page-title>

    <!-- 닉네임 입력 -->
    <label for="nickname" class="label-text"
      >사용할 닉네임을 입력해주세요.</label
    >
    <input
      type="text"
      id="nickname"
      class="input-text"
      name="nickname"
      v-model="nickname"
      placeholder="영문, 숫자 5~16자"
      autocapitalize="none"
    />
    <span class="alert" v-show="valid.nicknameType"
      >5자리 이상 16자 이하로 입력해주세요.</span
    >
    <span class="alert" v-show="valid.nickname">사용중인 닉네임입니다.</span>

    <!-- 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="addNickname"
    >
      가입하기
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import AccountsAPI from "@/apis/accountsAPI";
import ResponseData from "@/types/ResponseData";
import { mapState, mapActions } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "InsertNickname",
  components: {
    PageTitle,
  },
  data() {
    return {
      nickname: "",
      valid: {
        nicknameType: false,
        nickname: false,
      },
      isCompleted: false,
    };
  },
  computed: {
    ...mapState(accountsStore, ["joinInfo", "isLogin"]),
  },
  watch: {
    nickname: function () {
      if (!this.nickname) {
        this.isCompleted = false;
        this.valid.nickname = false;
      } else {
        this.checkNickname();
      }
    },
  },
  methods: {
    ...mapActions(accountsStore, ["getUserInfo", "getLogin", "storeEmail"]),
    // 닉네임 유효성 검사
    checkNickname() {
      // 닉네임 중복 검사
      AccountsAPI.checkNickname(this.nickname)
        .then((res: ResponseData) => {
          if (res.data === "success") {
            this.valid.nickname = false;
            this.isCompleted = true;
          } else {
            this.valid.nickname = true;
            this.isCompleted = false;
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // 닉네임 등록
    addNickname() {
      AccountsAPI.addNickname(this.joinInfo.email, this.nickname)
        .then((res: ResponseData) => {
          console.log(res);
          if (res.data === "success") {
            // 로그인 처리
            this.doLogin();
            // 로그인 처리 완료되면 가입정보 초기화 후 메인으로 이동
            this.storeEmail(null);
            this.$router.push({ name: "Main" });
          } else {
            alert("닉네임 등록 중 오류가 발생했습니다.");
          }
        })
        .catch(() => {
          alert("닉네임 등록 중 오류가 발생했습니다.");
        });
    },
    // 로그인 처리하기
    async doLogin() {
      // 이메일 회원가입일 경우 로그인 처리
      if (!this.isLogin) {
        await this.getLogin(this.joinInfo);
      }
      // 회원정보 받아와 저장하기
      await this.getUserInfo(this.joinInfo.email);
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";
</style>
