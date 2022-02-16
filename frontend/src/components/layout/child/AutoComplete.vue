<template>
  <div>
    <button @click="intersected">버튼</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ArtworkAPI from "@/apis/artworkAPI";

export default defineComponent({
  data() {
    return {
      page: 0,
      items: [] as any,
      artworkList: [] as any,
    };
  },
  methods: {
    async intersected() {
      const res = await ArtworkAPI.getArtworkList(this.page);
      const items = res.data;
      if (items.length === 20) {
        for (let i = 0; i < items.length; i++) {
          this.artworkList.push(items[i].artworkTitle);
        }
        this.page += 1;
        this.intersected();
      } else {
        for (let i = 0; i < items.length; i++) {
          this.artworkList.push(items[i].artworkTitle);
        }
        this.$router.push({ name:"NavBar", query:{ artworkList: this.artworkList }})
        console.log(this.artworkList);
        return this.artworkList;
      }
    },
  },
});
</script>

<style scoped>

</style>