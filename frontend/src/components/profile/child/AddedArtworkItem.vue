<template>
  <vue3-draggable-resizable
    class="artwork-box"
    :initW="100"
    :initH="120"
    :x="artwork.xloc"
    :y="artwork.yloc"
    :w="artwork.weight"
    :h="artwork.height"
    :active="active"
    :draggable="true"
    :resizable="true"
    :parent="true"
    @drag-end="updateLocation"
    @resize-end="updateSize"
  >
    <img :src="artwork.saveFolder" :alt="artwork.saveFileName" />
    <close-circle class="closeicon" @click="deleteArtwork"></close-circle>
  </vue3-draggable-resizable>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Vue3DraggableResizable from "vue3-draggable-resizable";
import { CloseCircle } from "mdue";
import { mapActions } from "vuex";

const galleryStore = "galleryStore";

export default defineComponent({
  name: "AddedArtworkItem",
  components: {
    Vue3DraggableResizable,
    CloseCircle,
  },
  props: {
    artwork: {},
    index: Number,
  },
  data() {
    return {
      x: 100,
      y: 100,
      h: 100,
      w: 100,
      active: false,
    };
  },
  methods: {
    ...mapActions(galleryStore, ["deleteArtworkInList"]),
    // 위치 정보 저장하기
    updateLocation(val: any) {
      this.x = val.x;
      this.y = val.y;
    },
    // 크기 정보 저장하기
    updateSize(val: any) {
      this.x = val.x;
      this.y = val.y;
      this.w = val.w;
      this.h = val.h;
    },
    deleteArtwork() {
      this.deleteArtworkInList(this.index);
    },
  },
});
</script>

<style lang="scss" scoped>
.artwork-box {
  img {
    width: 100%;
    height: 100%;
  }
  .closeicon {
    position: absolute;
    // top: 5;
    // right: 5;
  }
}
</style>
