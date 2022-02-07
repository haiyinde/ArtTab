<template>
  <div>
    <!-- <button v-on="$attr" class="[liked ? 'liked' : 'unliked']"><heart-outline></heart-outline></button> -->
    <button
      :class="{ 'disabled-button': disabled }"
    ><heart-outline></heart-outline></button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { HeartOutline } from "mdue";

export default defineComponent({
  props: {
    color: {
      type: String,
    },
    disabled: {
      type: Boolean,
      // default: () => false,
    },
  },
  components: {
    HeartOutline,
  }
})
</script>

<style lang="scss" scoped>
// .heart-outline {
//   color: $black;
//   // background: $black;
//   // cursor: pointer;
// }
.disabled-button {
  color: red;
  opacity: 0.5;
}

button {
  &:hover {
    filter: brightness(125%);
  }
  &:disabled {
    opacity: 0.5;
    color: red,
  }
}
.liked{ 
  color: $red;
}

.unliked{ 
  color: $black;
}
</style>