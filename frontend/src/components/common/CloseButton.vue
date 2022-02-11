<template>
  <close-circle class="profile-close-btn"></close-circle>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { CloseCircle } from "mdue";

export default defineComponent({
  props: {
    closed: {
      type: Boolean,
    },
  },
  components: {
    CloseCircle,
  },
});
</script>

<style lang="scss" scoped></style>
