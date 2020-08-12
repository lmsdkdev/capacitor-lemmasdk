import { PluginListenerHandle } from '@capacitor/core';

declare module global {
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


  addListener(eventName: 'onAdEvent', listenerFunc: (info: any) => void): PluginListenerHandle;

  addListener(eventName: 'onAdError', listenerFunc: (info: any) => void): PluginListenerHandle;

}


export interface AdOptions {
  pubId: string;       // Publisher id (required)
  adUnitId: string;       // Adunit id (required)

  baseServerURL?: string;
  width?: number;
  height?: number;
  bottomMargin?: number;
}