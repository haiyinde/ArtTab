export default interface FollowArtworkInfo {
  artworkInfo: [
    {
      artworkId: number;
      saveFolder: string;
    }
  ];
  artworkNum: number;
  followerNum: number;
  memberMail: string;
  recentUpdated: string;
}
