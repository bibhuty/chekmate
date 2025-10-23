import {DarkRoast, HouseBlend, Mocha, SteamedMilk, Whip} from "../../src/DecoratorPattern/StarBuzz";

describe('StarBuzz Decorator Pattern', () => {

    test('should combine descriptions correctly', () => {
        const beverage = new Whip(new Mocha(new DarkRoast()));
        expect(beverage.description()).toBe('Whipped Mocha Dark Roast');
    });

    test('should calculate total cost correctly', () => {
        const beverage = new Whip(new Mocha(new DarkRoast()));
        const baseCost = 0.99;
        const mochaCost = 0.20;
        const whipCost = 0.10;

        expect(beverage.cost()).toStrictEqual(baseCost+mochaCost+whipCost);
    });

    test('should allow independent combinations', () => {
        const milkHouseBlend = new SteamedMilk(new HouseBlend());
        const mochaDarkRoast = new Mocha(new DarkRoast());

        expect(milkHouseBlend.description()).toBe('Steamed Milk');
        expect(mochaDarkRoast.description()).toBe('Mocha Dark Roast');

        expect(milkHouseBlend.cost()).not.toBe(mochaDarkRoast.cost());
    });

});