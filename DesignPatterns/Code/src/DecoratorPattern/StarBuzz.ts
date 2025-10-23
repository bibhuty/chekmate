interface Beverage {
    description(): string;

    cost(): number;
}

export class HouseBlend implements Beverage {
    cost(): number {
        return .89;
    }

    description(): string {
        return 'House Blend';
    }
}

export class DarkRoast implements Beverage {
    cost(): number {
        return .99;
    }

    description(): string {
        return 'Dark Roast';
    }
}

interface Condiment extends Beverage {
    beverage: Beverage;
}

export class SteamedMilk implements Condiment {
    beverage: Beverage;

    constructor(beverage: Beverage) {
        this.beverage = beverage;
    }

    cost(): number {
        return .10 + this.beverage.cost();
    }

    description(): string {
        return 'Steamed Milk';
    }
}

export class Mocha implements Condiment {
    beverage: Beverage;

    constructor(beverage: Beverage) {
        this.beverage = beverage;
    }

    cost(): number {
        return .20 + this.beverage.cost();
    }

    description(): string {
        return 'Mocha ' + this.beverage.description();
    }

}

export class Whip implements Condiment {
    beverage: Beverage;

    constructor(beverage: Beverage) {
        this.beverage = beverage;
    }

    cost(): number {
        return .10 + this.beverage.cost();
    }

    description(): string {
        return 'Whipped ' + this.beverage.description();
    }
}