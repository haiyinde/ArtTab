<template>
  <div>
    <button :class="{ 'liked-color': liked }">
      <heart-outline></heart-outline>
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { HeartOutline } from "mdue";

export default defineComponent({
  props: {
    liked: {
      type: Boolean,
    },
  },
  components: {
    HeartOutline,
  },
});
</script>

<style lang="scss" scoped>
button {
  color: white;
  font-size: $size-large;
}

.liked-color {
  color: $red;
}
</style>
