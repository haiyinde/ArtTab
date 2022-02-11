<template>
  <button
    :class="{ followed: followed }"
    v-text="buttonText"
    @click="followUser"
  ></button>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  data() {
    return {
      status: false,
    };
  },
  props: {
    // user id랑 follows prop 받기
    followed: {
      type: Boolean,
    },
  },
  methods: {
    followUser() {
      // 여기에 axios 추가
      this.status = !this.status;
    },
  },
  computed: {
    buttonText() {
      return this.status ? "팔로우" : "언팔로우";
    },
  },
});
</script>

<style lang="scss" scoped>
button {
  font-weight: $weight-semi-bold;
  &:hover {
    color: $white;
  }
}
</style>
