import {describe, expect, it, beforeEach} from "vitest";
import {Duck, FlyNoWay, FlyWithWings, MallardDuck, RedheadDuck, RubberDuck, WoodenDuck} from "../../src/strategyPattern/SimUDuck";

describe("SimUDuck", () => {
    describe("MallardDuck", () => {
        let duck: Duck;
        beforeEach(() => {
            duck = new MallardDuck();
        })
        it("display's its name", () => {
            expect(duck.display()).toEqual("MallardDuck");
        })
        it("can fly", () => {
            expect(duck.performFly()).toEqual("Fly with Wings");
        })
    })
    describe("RedheadDuck", () => {
        let duck: Duck;
        beforeEach(() => {
            duck = new RedheadDuck();
        })
        it("displays its name", () => {
            expect(duck.display()).toEqual("RedheadDuck");
        })
        it("can fly", () => {
            expect(duck.performFly()).toEqual("Fly with Wings");
        })
    })
    describe("RubberDuck", () => {
        let duck: Duck;
        beforeEach(() => {
            duck = new RubberDuck();
        })
        it("displays its name", () => {
            expect(duck.display()).toEqual("RubberDuck");
        })
        it("can't fly", () => {
            expect(duck.performFly()).toEqual("Can't fly");
        })
    })
    describe("WoodenDuck", () => {
        let duck: Duck;
        beforeEach(() => {
            duck = new WoodenDuck();
        })
        it("displays its name", () => {
            expect(duck.display()).toEqual("WoodenDuck");
        })
        it("can't fly", () => {
            expect(duck.performFly()).toEqual("Can't fly");
        })
        it("forcing it to fly", () => {
            duck.flyBehaviour = new FlyWithWings();
            expect(duck.performFly()).toEqual("Fly with Wings");
        })
    })

})