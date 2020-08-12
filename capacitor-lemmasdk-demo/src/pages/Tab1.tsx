import React, { Component } from 'react';
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonCard, IonButton, IonCardContent } from '@ionic/react';
import { Plugins } from '@capacitor/core';
import { AdOptions } from 'capacitor-lemmasdk';
import './Tab1.css';
const { LemmaSDK, Toast } = Plugins;

class Tab1 extends Component {
  constructor(props: any) {
    super(props);
    this.state = {};
    LemmaSDK.initialize({});
  }

  // This Banner AD have bottom margin to avoid TabBar Overlaping for TabBar 
  showVideoAd() {

      const options: AdOptions = {
        pubId: '1',
        adUnitId: '3100',
        height: 160,
        bottomMargin:64 
      };

      LemmaSDK.showVideoAd(options)
      .then(
        async (value: any) => {
          console.log(value);  // true
          await Toast.show({
            text: 'Showing Lemma Video Ad.'
          })
        },
        (error: any) => {
          console.error(error); // show error
        }
      );


      // Subscibe Banner Event Listener
      LemmaSDK.addListener('onAdEvent', async (info: any) => {
        console.log('onAdEvent Ad. -> '+JSON.stringify(info));
      });

       // Subscibe Banner Event Listener
      LemmaSDK.addListener('onAdError', async (info: any) => {
        console.log('onAdError Ad. -> '+JSON.stringify(info));
      });

  }

  removeVideoAd() {
    LemmaSDK.removeVideoAd({})
      .then(
        async (value: any) => {
          console.log(value);  // true
          await Toast.show({
            text: 'Removed Lemma Video Ad.'
          })
        },
        (error: any) => {
          console.error(error); // show error
        }
      );
  }

  render() {
    return (
      <IonPage>
        <IonHeader>
          <IonToolbar color="dark">
            <IonTitle>Lemma Video Ad</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonContent className="ion-padding">
          <IonHeader collapse="condense">
            <IonToolbar>
              <IonTitle size="large">Video Ads</IonTitle>
            </IonToolbar>
          </IonHeader>
          <IonCard>
            <IonCardContent>Video ad will shown at the bottomt of the page.</IonCardContent>
          </IonCard>
          <IonButton expand="block" className="ion-margin-bottom" color="success" onClick={this.showVideoAd}>Show Video Ad</IonButton>
          <IonButton expand="block" className="ion-margin-bottom" color="warning" onClick={this.removeVideoAd}>Remove Banner Ad</IonButton>
        </IonContent>
      </IonPage>
    );
  };
}

export default Tab1;
