module.exports = {
  css: {
    loaderOptions: {
      scss: {
        additionalData: `
          @import "~@/assets/css/reset.scss";
          @import "~@/assets/css/common.scss";
        `,
      },
    },
  },
};
