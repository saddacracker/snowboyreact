/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Button
} from 'react-native';

import Hotword from './src/native/Hotword';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' + 'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {

  onPressStart() {
    Hotword.start();
  }

  onPressStop() {
    Hotword.stop();
  }


  componentDidMount() {
      Hotword.initHotword();
  }

  componentWillUnmount() {
    Hotword.destroy();
  }

  render() {
    return (
      <View style={styles.container}>
        <View>
          <Text style={styles.welcome}>
            Hello!
          </Text>
          <Text style={styles.instructions}>
            Say "Hey Hoss" to log detection.
          </Text>
          <Text style={styles.instructions}>
            {instructions}
          </Text>
        </View>

        <View style={styles.btnWrapper}>
          <Button
            onPress={() => {this.onPressStart()}}
            title="Start"
            color="#841584"
            accessibilityLabel="Start recording voice"
          />
        </View>
        <View style={styles.btnWrapper}>
          <Button
            onPress={() => {this.onPressStop()}}
            title="Stop"
            color="#841584"
            accessibilityLabel="Stop recording voice"
          />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#F5FCFF',
  },
  btnWrapper: {
    marginLeft: 60,
    marginRight: 60,
    marginTop: 20
  },
  welcome: {
    fontSize: 32,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    fontSize: 24,
    textAlign: 'center',
    color: '#333333',
    marginBottom: 10,
  },
});
