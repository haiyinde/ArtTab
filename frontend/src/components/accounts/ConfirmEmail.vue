<template>
  <div>
    <page-title title="이메일 인증하기" step="2" total="3"></page-title>

    <!-- 인증코드 입력 -->
    <label for="confirmCode" class="label-text"
      >이메일로 전송된 입증코드를 입력해주세요.</label
    >
    <input
      type="number"
      id="confirmCode"
      class="input-text"
      name="confirmCode"
      v-model="confirmCode"
      placeholder="인증코드 00자리 입력"
      autocapitalize="none"
    />
    <span class="alert" v-show="valid.confirmCode"
      >인증코드가 일치하지 않습니다.</span
    >

    <!-- 이메일 재전송 안내 -->
    <span class="guide"
      ><alert-circle-outline /> 이메일을 받지 못하셨나요?
      <span>이메일 재전송하기</span>
    </span>

    <!-- 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="moveInsertNickname"
    >
      다음
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import { AlertCircleOutline } from "mdue";

export default defineComponent({
  name: "ConfirmEmail",
  components: {
    PageTitle,
    AlertCircleOutline,
  },
  data() {
    return {
      confirmCode: "",
      valid: {
        confirmCode: false,
      },
      isCompleted: true,
    };
  },
  methods: {
    moveInsertNickname() {
      this.$router.push({ name: "InsertNickname" });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";
</style>
