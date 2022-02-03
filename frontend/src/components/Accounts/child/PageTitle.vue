<template>
  <div id="title">
    {{ title }}
    <div v-if="step" class="step">{{ step }} / {{ total }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "PageTitle",
  props: {
    title: String,
    step: String,
    total: String,
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/reset.scss";
@import "@/assets/css/common.scss";
#title {
  font-size: 1.5rem;
  font-weight: $weight-semi-bold;
  padding: $size-large 0;
  margin-bottom: $size-big;
  border-bottom: 1px solid $grey;
}

.step {
  font-size: $font-regular;
  text-align: end;
}
</style>
