declare module '@capacitor/core' {
  interface PluginRegistry {
    LemmaSDK: LemmaSDKPlugin;
  }
}

export interface LemmaSDKPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

   // Initialize Lemma SDK
  initialize(options: { pubId: string }): Promise<{ value: boolean }>

  // Show a video Ad
  showVideoAd(options: AdOptions): Promise<{ value: boolean }>;

  // Destroy the video, remove it from screen.
  removeVideoAd(): Promise<{ value: boolean }>;
}

export enum AdPosition {
  TOP_CENTER = 'TOP_CENTER',
  CENTER = 'CENTER',
  BOTTOM_CENTER = 'BOTTOM_CENTER',
}

export interface AdOptions {
  pubId: string;       // Publisher id (required)
  adUnitId: string;       // Adunit id (required)
  
  position?: AdPosition;
  width?: number;
  height?: number;
}