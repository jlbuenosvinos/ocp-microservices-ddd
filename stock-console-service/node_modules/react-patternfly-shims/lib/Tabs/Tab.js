"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require("react");

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**
 * React <b>Tab</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-tabs</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-tabs
 *
 * @example {@lang xml}
 * <Tabs tabChanged={ this.tabChanged(e) }>
 *  <Tab tabTitle={"Tab1"} active={true}>
 *    <p>Tab1 content here</p>
 *  </Tab>
 *  <Tab tabTitle={"Tab2"}>
 *    <p>Tab2 content here</p>
 *  </Tab>
 * </Tabs>
 *
 */
var Tab = function (_React$Component) {
  _inherits(Tab, _React$Component);

  function Tab() {
    _classCallCheck(this, Tab);

    return _possibleConstructorReturn(this, (Tab.__proto__ || Object.getPrototypeOf(Tab)).apply(this, arguments));
  }

  _createClass(Tab, [{
    key: "componentDidUpdate",
    value: function componentDidUpdate() {
      this.setActive();
    }
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      this.setActive();
    }
  }, {
    key: "setActive",
    value: function setActive() {
      if (this.props.active) {
        this.refs.pfTab.parentElement.setActiveTab(this.props.tabTitle || "");
      }
    }
  }, {
    key: "render",
    value: function render() {
      return _react2.default.createElement(
        "pf-tab",
        { tabTitle: this.props.tabTitle || "", active: this.props.active || false, key: "tab", ref: "pfTab" },
        this.props.children
      );
    }
  }]);

  return Tab;
}(_react2.default.Component);

Tab.propTypes = {
  tabTitle: _react2.default.PropTypes.string,
  active: _react2.default.PropTypes.bool
};
exports.default = Tab;