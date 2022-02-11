<template>
  <div class="toast" :class="{ reveal: isShow }">{{ message }}</div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ToastMessage",
  data() {
    return {
      isShow: false,
    };
  },
  props: {
    message: String,
  },
  methods: {
    showToast() {
      this.isShow = true;
      setTimeout(() => {
        this.isShow = false;
      }, 1000);
    },
  },
});
</script>

<style lang="scss" scoped>
.toast {
  position: fixed;
  top: 50px;
  left: 50%;
  transform: translateX(-50%);
  padding: 13px 18px;
  border-radius: 30px;
  overflow: hidden;
  font-size: 0.8rem;
  opacity: 0;
  visibility: hidden;
  transition: opacity 0.5s, visibility 0.5s, transform 0.5s;
  background: rgba(0, 0, 0, 0.35);
  color: #fff;
  z-index: 999;
  &.reveal {
    opacity: 1;
    visibility: visible;
    transform: translate(-50%, 0);
  }
}
</style>
