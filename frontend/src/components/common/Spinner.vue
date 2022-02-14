<template>
  <div class="spinner" v-if="isLoading"></div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    isLoading: {
      type: Boolean,
      required: true,
    },
  },
});
</script>

<style lang="scss" scoped>
@keyframes rotate {
  0% {
    transform: rotate(360deg);
  }
}
@keyframes wink {
  50% {
    opacity: 0;
  }
}
.spinner {
  width: 7em;
  margin: 3em auto;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  &:before {
    content: "";
    display: block;
    width: 7em;
    height: 1em;
    margin: 3em auto;
    padding: 3em 0;
    border-radius: 50%;
    border: 2px solid #2f416f;
    border-top: 2px solid transparent;
    border-bottom: 2px solid transparent;
    animation: rotate 1s ease infinite;
  }
  &:after {
    content: "Loading";
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    color: #2f416f;
    font: 0.9em sans-serif;
    width: 8em;
    line-height: 8em;
    text-align: center;
    animation: wink 1.4s ease infinite;
  }
}
</style>
