/**
 * @fileoverview This script creates and animates a scene 
 * with six 3-D objects. Three js is used to create the scene 
 * and objects.
 * 
 * @author Luis Moreno
 */
import * as THREE from 'three';

window.onload = function() {
    animate();
}

// create scene, renderer, and camera
const canvas = document.getElementById('canvas');
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 0.1, 100 );
camera.position.set(0, 0, 18);

const renderer = new THREE.WebGLRenderer();
renderer.setSize( window.innerWidth, window.innerHeight );
renderer.setClearColor( 0xffffff );
canvas.appendChild( renderer.domElement );

// flags for animation bounds
let stoppedCone = false;
let stoppedSphere = false;
let stoppedOct = false;
let scale = 1;

function animate() {
    if (isAnimating) {
        requestAnimationFrame( animate );

        cube.rotation.x += 0.01;
        cube.rotation.y += 0.01;

        torus.rotation.y += 0.01;

        cylinder.rotation.z += 0.01;
        cylinder.rotation.y += 0.01;

        octahedron.rotation.y += 0.02;
        octahedron.scale.set(scale, scale, scale);
        
        // bounds for translations and scaling
        if (stoppedCone) {
            cone.position.x += 0.1;
        } else {
            cone.position.x -= 0.1;
        }

        if (stoppedSphere) {
            sphere.position.z -= 0.1;
        } else {
            sphere.position.z += 0.1;
        }

        if (stoppedOct) {
            scale -= 0.01;
        } else {
            scale += 0.01;
        }

        if (cone.position.x < -11) {
            stoppedCone = true;
        }

        if (cone.position.x > 11) {
            stoppedCone = false;
        }

        if (sphere.position.z < 0) {
            stoppedSphere = false;
        }

        if (sphere.position.z > 16) {
            stoppedSphere = true;
        }

        if (scale > 2.5) {
            stoppedOct = true;
        }

        if (scale < 0) {
            stoppedOct = false;
        }

        renderer.render( scene, camera );
    }
}

// shapes
const geometryCube = new THREE.BoxGeometry( 2, 2, 2 );
const material = new THREE.MeshLambertMaterial( { color: 0xffffff } );
const cube = new THREE.Mesh( geometryCube, material );
scene.add( cube );

const geometryTorus = new THREE.TorusGeometry(4, 1, 10, 100);
const torus = new THREE.Mesh( geometryTorus, material );
torus.position.set(0, 0, 0);
scene.add( torus );

const geometryCone = new THREE.ConeGeometry(1, 2, 32);
const cone = new THREE.Mesh( geometryCone, material );
cone.position.set(0, 7, 0);
scene.add( cone );

const geometryCylinder = new THREE.CylinderGeometry(1, 1, 8, 32);
const cylinder = new THREE.Mesh( geometryCylinder, material );
cylinder.position.set(9, 0, 1);
scene.add( cylinder );

const geometrySphere = new THREE.SphereGeometry(1, 32, 32);
const sphere = new THREE.Mesh( geometrySphere, material );
sphere.position.set(-7, 0, 0);
scene.add( sphere );

const geometryOctahedron = new THREE.OctahedronGeometry(1);
const octahedron = new THREE.Mesh( geometryOctahedron, material );
octahedron.position.set(0, -6, 3);
octahedron.rotation.x += 3;
scene.add( octahedron );

// lights
const pointLight = new THREE.PointLight(0xff0000, 0);
pointLight.position.set(-8, -5, 6);
pointLight.castShadow = false;
scene.add( pointLight );

const directionalLight = new THREE.DirectionalLight(0xffffff, 1);
directionalLight.position.set(0, 0, 20);
directionalLight.castShadow = false;
scene.add( directionalLight );

const pointLightTwo = new THREE.PointLight(0xff0000, 0);
pointLightTwo.position.set(11, 8, 3);
pointLightTwo.castShadow = false;
scene.add( pointLightTwo );

// button and slider event listeners
const toggleAnimation = document.getElementById('toggle-animation');
const lightOne = document.getElementById('light1');
const lightTwo = document.getElementById('light2');
const lightThree = document.getElementById('light3');

let isAnimating = true;

// animate checkbox button event listener
toggleAnimation.addEventListener('click', () => {
    isAnimating = toggleAnimation.checked;
    if (toggleAnimation.checked) {
        isAnimating = true;
        animate();
    } else {
        isAnimating = false;
    }
});

// light checkbox button event listeners
lightOne.addEventListener('click', () => {
    if (lightOne.checked) {
        directionalLight.intensity = 1;
    } else {
        directionalLight.intensity = 0;
    }
});

lightTwo.addEventListener('click', () => {
    if (lightTwo.checked) {
        pointLight.intensity = 1;
    } else {
        pointLight.intensity = 0;
    }
});

lightThree.addEventListener('click', () => {
    if (lightThree.checked) {
        pointLightTwo.intensity = 1;
    } else {
        pointLightTwo.intensity = 0;
    }
});

// Slider event listeners
document.getElementById('light1-slider').addEventListener('input', function() {
    const color = getColorFromSliderValue(document.getElementById('light1-slider').value);
    //console.log(color);
    directionalLight.color.set(color);
})

document.getElementById('light2-slider').addEventListener('input', function() {
    const color = getColorFromSliderValue(document.getElementById('light2-slider').value);
    //console.log(color);
    pointLight.color.set(color);
})

document.getElementById('light3-slider').addEventListener('input', function() {
    const color = getColorFromSliderValue(document.getElementById('light3-slider').value);
    //console.log(color);
    pointLightTwo.color.set(color);
})

/**
 * Sliders for each light allow the user to change the light color.
 * This function does the calculations to correctly set the color based off its position.
 * @param {*} value position of slider thumb
 * @returns rgb values of a color depending on the location of the slider thumb
 */
function getColorFromSliderValue(value) {
    const colors = [
        { position: 0, color: new THREE.Color('white') },
        { position: 12.5, color: new THREE.Color('red') },
        { position: 25, color: new THREE.Color('orange') },
        { position: 37.5, color: new THREE.Color('yellow') },
        { position: 50, color: new THREE.Color('green') },
        { position: 62.5, color: new THREE.Color('blue') },
        { position: 75, color: new THREE.Color('indigo') },
        { position: 87.5, color: new THREE.Color('violet') },
        { position: 100, color: new THREE.Color('black') }
    ];
  
    let color;
  
    for (let i = 0; i < colors.length - 1; i++) {
      if (value >= colors[i].position && value <= colors[i+1].position) {
        const t = (value - colors[i].position) / (colors[i+1].position - colors[i].position);
        color = colors[i].color.clone().lerp(colors[i+1].color, t);
        break;
      }
    }
  
    return color;
}