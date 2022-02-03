<template>
  <div id="accounts">
    <router-view />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Accounts",
  components: {},
});
</script>

<style lang="scss" scoped>
#accounts {
  width: 450px;
  text-align: left;
  margin: 3rem auto;
  padding: 3rem $size-large;
  border: 1px solid $grey;
}

/* 모바일 크기에서 적용되는 스타일 */
@media screen and (max-width: 450px) {
  #accounts {
    width: 100%;
    border: none;
  }
}
</style>
