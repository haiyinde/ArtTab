<template>
  <div class="password-box">
    <input
      :type="isShowPwd ? 'text' : 'password'"
      class="input-text"
      name="password"
      :value="password"
      @input="handleInput"
      :placeholder="placetext"
    />
    <eye-off-outline
      v-show="!isShowPwd"
      @click="togglePwd"
      class="icon"
    ></eye-off-outline>
    <eye-outline
      v-show="isShowPwd"
      @click="togglePwd"
      class="icon"
    ></eye-outline>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { EyeOffOutline, EyeOutline } from "mdue";

export default defineComponent({
  props: {
    password: String,
    placetext: String,
  },
  components: {
    EyeOffOutline,
    EyeOutline,
  },
  data() {
    return {
      isShowPwd: false,
    };
  },
  methods: {
    handleInput(e: InputEvent) {
      const eTarget = e.target as HTMLInputElement;
      this.$emit("inputVal", eTarget.value);
    },
    // 비밀번호 보기/숨기기
    togglePwd() {
      this.isShowPwd = !this.isShowPwd;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/reset.scss";
@import "@/assets/css/common.scss";
@import "@/assets/css/accounts.scss";

.password-box {
  position: relative;
  .icon {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: $size-regular;
    font-size: $font-medium;
    cursor: pointer;
  }
}
</style>
