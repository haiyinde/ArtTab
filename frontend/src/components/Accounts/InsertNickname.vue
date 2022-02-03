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
      @click="moveInsertNickname"
    >
      가입하기
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/Accounts/child/PageTitle.vue";

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
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/reset.scss";
@import "@/assets/css/common.scss";
@import "@/assets/css/accounts.scss";
</style>
