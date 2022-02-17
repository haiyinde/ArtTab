<template>
  <div v-if="!contentsDone">
    <div class="observer" />
  </div>
</template>

<script>
export default {
  props: ["options"],
    contentsDone: {
      type: Boolean,
      required: true,
    },
  data: () => ({
    observer: null,
  }),
  mounted() {
    const options = this.options || {};
    this.observer = new IntersectionObserver(([entry]) => {
      if (entry && entry.isIntersecting) {
        this.$emit("intersect");
      }
    }, options);

    this.observer.observe(this.$el);
  },
  unmounted() {
    this.observer.disconnect();
  },
};
</script>
