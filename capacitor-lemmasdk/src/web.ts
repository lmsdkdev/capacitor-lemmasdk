import { WebPlugin } from '@capacitor/core';
import { LemmaSDKPlugin } from './definitions';

export class LemmaSDKWeb extends WebPlugin implements LemmaSDKPlugin {
  constructor() {
    super({
      name: 'LemmaSDK',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const LemmaSDK = new LemmaSDKWeb();

export { LemmaSDK };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(LemmaSDK);
