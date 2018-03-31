import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { LinearGradient } from "expo";
import HomeScreen from "./src/screens/Homescreen";
// import Faqscreen from "./src/screens/Faqscreen";
// import Resultscreen from "./src/screens/Resultscreen";
// import ResultNotFound from "./src/screens/ResultNotFound";
import Secure from './src/screens/Secure'
import { FlipX, createTransition } from "react-native-transition";

const Transition = createTransition(FlipX);

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoaded: false,
      result: null
    };
    this.transition = this.transition.bind(this);
    this.rnfTransition = this.rnfTransition.bind(this);
    this.backToMainScreen = this.backToMainScreen.bind(this);
  }

  transition(type) {
    Transition.show(<Secure data={type} />);
  }
  backToMainScreen() {
    Transition.show(
      <HomeScreen
        onS={this.transition}
        onResultNotFound={this.rnfTransition}
      />
    );
  }
  rnfTransition() {
    Transition.show( <ResultNotFound  onBack={this.backToMainScreen} />);
  }
  render() {
    return (
      <View style={styles.container}>
        <LinearGradient
          style={{ flex: 1 }}
          colors={["rgb(62,72,103)", "rgb(41,49,74)"]}
        >
          <Transition>
            <HomeScreen
              onS={this.transition}
              onResultNotFound={this.rnfTransition}
            />
          </Transition>
        </LinearGradient>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1
  }
});
