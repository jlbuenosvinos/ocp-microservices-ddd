'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**
 * React <b>Tabs</b> Component for Patternfly Web Components
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
var Tabs = function (_React$Component) {
  _inherits(Tabs, _React$Component);

  function Tabs() {
    _classCallCheck(this, Tabs);

    return _possibleConstructorReturn(this, (Tabs.__proto__ || Object.getPrototypeOf(Tabs)).apply(this, arguments));
  }

  _createClass(Tabs, [{
    key: 'componentDidUpdate',
    value: function componentDidUpdate() {
      var _this2 = this;

      this.refs.pfTabs.addEventListener('tabChanged', function (e) {
        if (_this2.props.tabChanged) {
          _this2.props.tabChanged(e);
        }
      });
    }
  }, {
    key: 'render',
    value: function render() {
      return _react2.default.createElement(
        'pf-tabs',
        { key: 'pf-tabs', ref: 'pfTabs' },
        this.props.children
      );
    }
  }]);

  return Tabs;
}(_react2.default.Component);

Tabs.propTypes = {
  tabChanged: _react2.default.PropTypes.func
};
exports.default = Tabs;