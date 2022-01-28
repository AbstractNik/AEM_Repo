/*
 *
 * Copyright 2016 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
define('instore/views/scrollable-panel', [
    'underscore',
    'jquery',
    'util/Util',
    'views/BaseView'
], function(_, $, Util, BaseView) {
    'use strict';

    /**
     * Default view options.
     *
     * @type {Map}
     * @static
     */
    var DEFAULT_OPTIONS = {
        scrollLines: 6
    };

    var ScrollablePanelView = BaseView.extend(/** @lends ScrollablePanelView.prototype */ {

        /**
         * Class name for the wrapping element of the view.
         *
         * @type {String}
         */
        className: 'instore-ScrollablePanelView',

        /**
         * Sistine options.
         *
         * @type {Object}
         */
        events: {
            'click .icon-scroll': '_handleScroll'
        },

        /**
         * @constructor
         * @extends BaseView
         *
         * @param {Object} [options] An object of configurable options.
         */
        constructor: function(options) {
            this._initOptions(options, DEFAULT_OPTIONS);
            ScrollablePanelView.__super__.constructor.apply(this, arguments);
            this._sistine.add(new Sistine.Tap({
                eventName: 'tap',
                taps: 1,
                delegate: this.delegate,
                delegateScope: this
            }));
        },

        /**
         * Handles scrolling the product summary view.
         *
         * @param {Event} ev The event that triggered the action
         */
        _handleScroll: function(ev) {
            var $summary = this.$el.find('.instore-ScrollablePanelView-content');
            var lineHeight = parseFloat($summary.css('line-height'));
            var direction = $(ev.target).hasClass('icon-scroll--up') ? -1 : 1;
            $summary.scrollTop($summary.scrollTop() + direction * this.options.scrollLines * lineHeight);
            this.update();
        },

        /**
         * Update the view and toggle the scroll buttons as needed.
         */
        update: function() {
            var $summary = this.$el.find('.instore-ScrollablePanelView-content');
            this.$el.find('.icon-scroll--up').toggle($summary.scrollTop() > 0);
            this.$el.find('.icon-scroll--down').toggle(
                $summary.scrollTop() + $summary.height() < $summary.get(0).scrollHeight);
        },

        /**
         * Instantiate the template markup with the provided data.
         *
         * @param {Object} panel The panel to render the template with.
         * @returns {String} the HTML string for the instantiated template
         */
        _template: function(panel) {
            var tpl = _.template(
                '<button class="icon icon-scroll icon-scroll--up"></button>' +
                '<div class="instore-ScrollablePanelView-content"><%= content %></div>' +
                '<button class="icon icon-scroll icon-scroll--down"></button>'
            );
            return tpl({
                content: panel.content
            });
        },

        /**
         * Render the view.
         *
         * @returns {ScrollablePanelView} the view
         */
        render: function() {
            // prevent re-render
            if (this.setRendered(true)) {
                return this;
            }
            this.$el.prepend(this._template(this.model));

            var $summary = this.$el.find('.instore-ScrollablePanelView-content');
            var $scrollUp = this.$el.find('.icon-scroll--up');
            var $scrollDown = this.$el.find('.icon-scroll--down');
            window.setTimeout(function() {
                $scrollUp.hide();
                $scrollDown.toggle($summary.height() < $summary.get(0).scrollHeight);
            });

            return this;
        }

    });

    // return the view
    return ScrollablePanelView;

});
