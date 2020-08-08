import React, { Component } from 'react';
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonCard, IonButton, IonCardContent } from '@ionic/react';
import { Plugins } from '@capacitor/core';
import { AdOptions, AdPosition } from 'capacitor-lemmasdk';
import './Tab1.css';
const { LemmaSDK, Toast } = Plugins;

class Tab1 extends Component {
  constructor(props: any) {
    super(props);
    this.state = {};
    LemmaSDK.initialize({'pubId':'ca-app-pub-3940256099942544~3347511713'});
  }

  // This Banner AD have bottom margin to avoid TabBar Overlaping for TabBar 
  showTabBarBanner() {

      const options: AdOptions = {
      pubId: 'pubid-xxx',
      adUnitId: 'adunit-xxx'
      };

      LemmaSDK.showVideoAd(options)
      .then(
        async (value: any) => {
          console.log(value);  // true
          await Toast.show({
            text: 'Showing Banner AD.'
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
            <IonTitle>Banner Ads</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonContent className="ion-padding">
          <IonHeader collapse="condense">
            <IonToolbar>
              <IonTitle size="large">Banner Ads</IonTitle>
            </IonToolbar>
          </IonHeader>
          <IonCard>
            <IonCardContent>Banner Ad is usually shown by default on bottom of the page. You can select the position as you like.
      You can even display multiple banner ads per page.</IonCardContent>
          </IonCard>
          <IonButton expand="block" className="ion-margin-bottom" color="success" onClick={this.showTabBarBanner}>Show Banner Ad</IonButton>
        </IonContent>
      </IonPage>
    );
  };
}

export default Tab1;
